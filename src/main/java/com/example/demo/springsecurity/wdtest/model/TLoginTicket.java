package com.example.demo.springsecurity.wdtest.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

public class TLoginTicket implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer ltId;

    private Long uid;

    private String status;

    private Date expired;

    private String ticket;

    private static final long serialVersionUID = 1L;

    public Integer getLtId() {
        return ltId;
    }

    public void setLtId(Integer ltId) {
        this.ltId = ltId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ltId=").append(ltId);
        sb.append(", uid=").append(uid);
        sb.append(", status=").append(status);
        sb.append(", expired=").append(expired);
        sb.append(", ticket=").append(ticket);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}