package com.bazinga.SimRacingSeries_Backend.services;

import com.bazinga.SimRacingSeries_Backend.model.SeriesDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private SeriesService seriesService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SeriesDO series = seriesService.getSeries(username);
        if(series != null) {
            return new User(series.getSlugName(), series.getPassword(), AuthorityUtils.createAuthorityList(series.getId()));
        }
        return null;
    }

}

