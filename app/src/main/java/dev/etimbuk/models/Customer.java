package dev.etimbuk.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "customers")
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "joinedOn")
    private LocalDate joinedOn;

    public Customer(String username, String fullName, LocalDate joinedOn) {
        this.username = username;
        this.fullName = fullName;
        this.joinedOn = joinedOn;
    }

}
