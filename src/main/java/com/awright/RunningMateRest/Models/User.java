package com.awright.RunningMateRest.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import com.awright.RunningMateRest.DTO.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class User implements UserDetails {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @GeneratedValue
    @Id
    private Integer id;
    private String name;
    private String password;
    private String email;
    private boolean waiting;
    private boolean readyToRun;
    @ManyToMany
    private List<Instance> instances;
    @Embedded
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public User(UserDto userDto) {
        this.name = userDto.getUserName();
        this.password = userDto.getPassword();
        this.email = userDto.getEmail();
        this.authorities.add(new SimpleGrantedAuthority("USER"));
        waiting = false;
    }

    public void addInstance (Instance instance){
        this.instances.add(instance);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void removeInstance (Instance instance){
        this.instances.remove(instance);
    }

    // public void addChallenege(User user){
    //     this.challenges.add(user);
    // }
}
