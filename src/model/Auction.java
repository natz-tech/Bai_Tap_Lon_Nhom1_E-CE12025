package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Auction {

    public enum AuctionStatus {
        OPEN, RUNNING, FINISHED, PAID, CANCELED
    }

    private String auctionId;
    private Item item;
    private double currentHighestBid;
    private List<Bid> bids;
    private AuctionStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private List<AuctionObserver> observers; 

    private final ReentrantLock lock = new ReentrantLock();

    public Auction(String auctionId, Item item, double startingPrice, LocalDateTime startTime, LocalDateTime endTime) {
        this.auctionId = auctionId;
        this.item = item;
        this.currentHighestBid = startingPrice;
        this.bids = new ArrayList<>();
        this.status = AuctionStatus.OPEN;
        this.startTime = startTime;
        this.endTime = endTime;
        this.observers = new ArrayList<>();
    }

    public void addObserver(AuctionObserver observer) {
        lock.lock();
        try {
            if (!observers.contains(observer)) {
                observers.add(observer);
            }
        } finally {
            lock.unlock();
        }
    }

    public void removeObserver(AuctionObserver observer) {
        lock.lock();
        try {
            observers.remove(observer);
        } finally {
            lock.unlock();
        }
    }

    public void notifyObserversNewBid(Bid bid) {
        List<AuctionObserver> snapshot;
        lock.lock();
        try {
            snapshot = new ArrayList<>(observers);
        } finally {
            lock.unlock();
        }
        
        for (AuctionObserver observer : snapshot) {
            observer.onNewBid(this, bid);
        }
    }

    public void notifyObserversAuctionFinished() {
        List<AuctionObserver> snapshot;
        lock.lock();
        try {
            snapshot = new ArrayList<>(observers);
        } finally {
            lock.unlock();
        }

        for (AuctionObserver observer : snapshot) {
            observer.onAuctionFinished(this);
        }
    }

    public boolean addBid(Bid bid) {
        boolean success = false;
        lock.lock();
        try {
            checkAndUpdateStatus();

            if (status != AuctionStatus.RUNNING) {
                System.out.println("Khong the dat gia: Phien dau gia khong o trang thai RUNNING (Trang thai hien tai: " + status + ")");
                return false;
            }

            if (bid == null || bid.getAmount() <= currentHighestBid) {
                System.out.println("Khong the dat gia: Gia dat phai cao hon gia hien tai (" + currentHighestBid + ")");
                return false;
            }

            currentHighestBid = bid.getAmount();
            if (item != null) {
                item.setCurrentHighestBid(currentHighestBid);
            }
            bids.add(bid);
            success = true;
        } finally {
            lock.unlock();
        }

        if (success) {
            notifyObserversNewBid(bid);
        }
        return success;
    }

    public void checkAndUpdateStatus() {
        lock.lock();
        try {
            if (status == AuctionStatus.PAID || status == AuctionStatus.CANCELED || status == AuctionStatus.FINISHED) {
                return;
            }

            LocalDateTime now = LocalDateTime.now();

            if (status == AuctionStatus.OPEN && startTime != null && !now.isBefore(startTime)) {
                startAuction();
            }

            if (status == AuctionStatus.RUNNING && endTime != null && now.isAfter(endTime)) {
                finishAuction();
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean startAuction() {
        lock.lock();
        try {
            if (status != AuctionStatus.OPEN) {
                System.out.println("Loi chuyen trang thai: Chi phien dang OPEN moi co the chuyen sang RUNNING!");
                return false;
            }
            this.status = AuctionStatus.RUNNING;
            System.out.println("Phien dau gia [" + auctionId + "] da bat dau: OPEN -> RUNNING");
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean finishAuction() {
        boolean changed = false;
        lock.lock();
        try {
            if (status != AuctionStatus.RUNNING) {
                System.out.println("Loi chuyen trang thai: Chi phien dang RUNNING moi co the chuyen sang FINISHED!");
                return false;
            }
            this.status = AuctionStatus.FINISHED;
            System.out.println("Phien dau gia [" + auctionId + "] da ket thuc: RUNNING -> FINISHED");
            changed = true;
        } finally {
            lock.unlock();
        }
        
        if (changed) {
            notifyObserversAuctionFinished();
        }
        return changed;
    }

    public boolean markAsPaid() {
        lock.lock();
        try {
            if (status != AuctionStatus.FINISHED) {
                System.out.println("Loi chuyen trang thai: Chi phien da FINISHED moi co the chuyen sang PAID!");
                return false;
            }
            if (bids.isEmpty()) {
                System.out.println("Loi thanh toan: Khong co nguoi thang cuoc trong phien dau gia nay!");
                return false;
            }
            this.status = AuctionStatus.PAID;
            System.out.println("Phien dau gia [" + auctionId + "] da thanh toan: FINISHED -> PAID");
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean cancelAuction() {
        lock.lock();
        try {
            if (status == AuctionStatus.PAID) {
                System.out.println("Loi chuyen trang thai: Khong the huy phien dau gia da thanh toan (PAID)!");
                return false;
            }
            if (status == AuctionStatus.CANCELED) {
                System.out.println("Phien dau gia [" + auctionId + "] da o trang thai CANCELED.");
                return false;
            }
            AuctionStatus oldStatus = this.status;
            this.status = AuctionStatus.CANCELED;
            System.out.println("Phien dau gia [" + auctionId + "] da bi huy: " + oldStatus + " -> CANCELED");
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void setStatus(AuctionStatus newStatus) {
        lock.lock();
        try {
            if (newStatus == null || this.status == newStatus) {
                return;
            }
            switch (newStatus) {
                case RUNNING:
                    startAuction();
                    break;
                case FINISHED:
                    finishAuction();
                    break;
                case PAID:
                    markAsPaid();
                    break;
                case CANCELED:
                    cancelAuction();
                    break;
                default:
                    System.out.println("Khong the quay nguoc ve trang thai: " + newStatus);
            }
        } finally {
            lock.unlock();
        }
    }

    public Bid getHighestBid() {
        lock.lock();
        try {
            if (bids == null || bids.isEmpty()) {
                return null;
            }
            return bids.get(bids.size() - 1);
        } finally {
            lock.unlock();
        }
    }

    public User getWinner() {
        lock.lock();
        try {
            if (status != AuctionStatus.FINISHED && status != AuctionStatus.PAID) {
                return null;
            }
            Bid winningBid = getHighestBid();
            return (winningBid != null) ? winningBid.getBidder() : null;
        } finally {
            lock.unlock();
        }
    }

    public double getCurrentHighestBid() {
        lock.lock();
        try {
            return currentHighestBid;
        } finally {
            lock.unlock();
        }
    }

    public AuctionStatus getStatus() {
        lock.lock();
        try {
            return status;
        } finally {
            lock.unlock();
        }
    }

    public List<Bid> getBids() {
        lock.lock();
        try {
            return new ArrayList<>(bids);
        } finally {
            lock.unlock();
        }
    }

    public String getAuctionId() {
        return auctionId;
    }

    public Item getItem() {
        return item;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
