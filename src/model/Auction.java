package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Auction(String auctionId, Item item, double startingPrice, LocalDateTime startTime, LocalDateTime endTime) {
        this.auctionId = auctionId;
        this.item = item;
        this.currentHighestBid = startingPrice;
        this.bids = new ArrayList<>();
        this.status = AuctionStatus.OPEN;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public synchronized boolean addBid(Bid bid) {
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
        return true;
    }

    public synchronized void checkAndUpdateStatus() {
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
    }

    public synchronized boolean startAuction() {
        if (status != AuctionStatus.OPEN) {
            System.out.println("Loi chuyen trang thai: Chi phien dang OPEN moi co the chuyen sang RUNNING!");
            return false;
        }
        this.status = AuctionStatus.RUNNING;
        System.out.println("Phien dau gia [" + auctionId + "] da bat dau: OPEN -> RUNNING");
        return true;
    }

    public synchronized boolean finishAuction() {
        if (status != AuctionStatus.RUNNING) {
            System.out.println("Loi chuyen trang thai: Chi phien dang RUNNING moi co the chuyen sang FINISHED!");
            return false;
        }
        this.status = AuctionStatus.FINISHED;
        System.out.println("Phien dau gia [" + auctionId + "] da ket thuc: RUNNING -> FINISHED");
        return true;
    }

    public synchronized boolean markAsPaid() {
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
    }

    public synchronized boolean cancelAuction() {
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
    }

    public synchronized void setStatus(AuctionStatus newStatus) {
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
    }

    public synchronized Bid getHighestBid() {
        if (bids == null || bids.isEmpty()) {
            return null;
        }
        return bids.get(bids.size() - 1);
    }

    public synchronized User getWinner() {
        if (status != AuctionStatus.FINISHED && status != AuctionStatus.PAID) {
            return null;
        }
        Bid winningBid = getHighestBid();
        return (winningBid != null) ? winningBid.getBidder() : null;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public Item getItem() {
        return item;
    }

    public double getCurrentHighestBid() {
        return currentHighestBid;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}