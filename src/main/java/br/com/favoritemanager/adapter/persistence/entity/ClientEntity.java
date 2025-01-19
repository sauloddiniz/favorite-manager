package br.com.favoritemanager.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CLIENT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ClientEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID")
    @EqualsAndHashCode.Include
    private Long clientId;
    @EqualsAndHashCode.Include
    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL", unique = true)
    @EqualsAndHashCode.Include
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ProductEntity> favoriteProducts = new HashSet<>();


}
