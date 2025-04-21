package com.web.studentsApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Phone extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @Id
    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_type", length = 20)
    private PhoneType phoneType;

    @Column(name = "country_code", length = 5)
    private String countryCode;

    @Column(name = "area_code", length = 5)
    private String areaCode;

    public enum PhoneType {
        MOBILE, HOME, WORK, EMERGENCY
    }
}


   // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "phone_id")
    // private Long phoneId;