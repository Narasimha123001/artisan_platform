package org.techtricks.artisanPlatform.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.exceptions.ArtisanAlreadyExistsException;
import org.techtricks.artisanPlatform.exceptions.ArtisanNotFoundException;
import org.techtricks.artisanPlatform.models.Artisan;
import org.techtricks.artisanPlatform.repositories.ArtisanRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArtisanServiceImpl implements ArtisanService {

    private final ArtisanRepository artisanRepository;

    private final PasswordEncoder passwordEncoder;

    public ArtisanServiceImpl(ArtisanRepository artisanRepository, PasswordEncoder passwordEncoder) {
        this.artisanRepository = artisanRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Artisan> getAllArtisan() {
        return artisanRepository.findAll();
    }

    @Override
    public Artisan saveArtisan(Artisan artisan) throws ArtisanAlreadyExistsException {
        Optional<Artisan> artisanOptional = artisanRepository.findByEmail(artisan.getEmail());
        if(artisanOptional.isPresent()) {
            throw new ArtisanAlreadyExistsException("Artisan already exists with this email:"+ artisan.getEmail() +",please login with another email");
        }
        artisan.setPassword(passwordEncoder.encode(artisan.getPassword()));
        return artisanRepository.save(artisan);
    }

    @Override
    public Artisan getByUsername(String username) throws ArtisanNotFoundException {
        Optional<Artisan> artisanOptional = artisanRepository.findByUserName(username);
        if(artisanOptional.isEmpty())
            throw new ArtisanNotFoundException("Artisan not found on this username "+username+" , please try another email");
        return artisanOptional.get();
    }
}
