package dev.feramaro.urlshortener.repositories;

import dev.feramaro.urlshortener.entities.Url;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface UrlRepository extends CrudRepository<Url, String> {

    Optional<Url> findByShortCode(String shortCode);

}
