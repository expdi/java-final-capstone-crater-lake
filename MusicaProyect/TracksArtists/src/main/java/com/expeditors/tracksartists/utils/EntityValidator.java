package com.expeditors.tracksartists.utils;

import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public final class EntityValidator {
    public static <T> T getIfIsValidEntity(JpaRepository<T, Integer> repository, int id){
        Optional<T> entity = repository.findById(id);

        if(entity.isEmpty()){
            throw new WrongRequestException("The id does not match with our records", HttpStatus.NOT_FOUND, id);
        }

        return entity.get();
    }
}
