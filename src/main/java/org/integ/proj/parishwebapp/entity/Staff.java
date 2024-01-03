package org.integ.proj.parishwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Staff")
public class Staff {
//	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String fname;
    
    @Column(nullable=false)
    private String mname;
    
    @Column(nullable=false)
    private String lname;
    
    @Column(nullable=false)
    private LocalDate employementDate;
    
    @Column
    private String employementStatus;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;
    
    @Column
    private LocalDate birthdate;

    
    @Column
    private String phoneNumber;
    
    @Column
    private String address;
    
    @Column
    private String position;
    
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    private Role role;
    
}
