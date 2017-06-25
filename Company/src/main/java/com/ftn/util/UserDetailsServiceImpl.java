package com.ftn.util;

import com.ftn.model.database.Zaposleni;
import com.ftn.repository.ZaposleniDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Olivera on 17.6.2017..
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ZaposleniDao zaposleniDao;

    @Autowired
    public UserDetailsServiceImpl(ZaposleniDao zaposleniDao) {
        this.zaposleniDao = zaposleniDao;
    }

    @Override
    public UserDetails loadUserByUsername(String korisnickoIme) throws UsernameNotFoundException {

        final Zaposleni zaposleni = zaposleniDao.findByKorisnickoIme(korisnickoIme);
        if (zaposleni == null) {
            throw new UsernameNotFoundException("zaposleni nije pronadjen");
        }

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                final Collection<GrantedAuthority> authorities = new ArrayList<>();
                //authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
                return authorities;
            }

            @Override
            public String getPassword() {
                return zaposleni.getLozinka();
            }

            @Override
            public String getUsername() {
                return zaposleni.getKorisnickoIme();
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

        };

    }
}
