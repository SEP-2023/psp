package com.project.paypal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "subscriber_mail")
    private String subscriberMail;

    @Column(name="subscriber_id")
    private String subscriberId;

    @Column(name="token")
    private String token;

    @Column(name="status")
    private SubscriptionStatus status;

    @Column(name="transactionId")
    private String transactionId;


}
