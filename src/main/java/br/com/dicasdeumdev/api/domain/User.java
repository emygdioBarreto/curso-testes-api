package br.com.dicasdeumdev.api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "tb_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    @Length(min = 4, max = 40)
    @Column(name = "nome")
    private String name;

    @NotNull
    @NotBlank
    @Length(min = 4, max = 80)
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @NotBlank
    @Length(min = 4, max = 80)
    @Column(name = "senha")
    private String password;
}
