package sn.zahra.thiaw.mydependenciesspring.Web.Controllers.Impl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sn.zahra.thiaw.mydependenciesspring.Exceptions.BadRequestException;
import sn.zahra.thiaw.mydependenciesspring.Exceptions.ResourceNotFoundException;
import sn.zahra.thiaw.mydependenciesspring.Exceptions.UnauthorizedException;
import sn.zahra.thiaw.mydependenciesspring.Exceptions.ValidationException;
import sn.zahra.thiaw.mydependenciesspring.Web.Controllers.BaseController;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseControllerImpl<E, ID, R> implements BaseController<E, ID, R> {

    private final sn.zahra.thiaw.mydependenciesspring.Services.BaseService<E, ID> service;
    private final sn.zahra.thiaw.mydependenciesspring.Mappers.GenericMapper<E, ?, R> mapper;

    public BaseControllerImpl(sn.zahra.thiaw.mydependenciesspring.Services.BaseService<E, ID> service,
                              sn.zahra.thiaw.mydependenciesspring.Mappers.GenericMapper<E, ?, R> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<R> create(E entity) {
        try {
            E createdEntity = service.create(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(createdEntity));
        } catch (ValidationException e) {
            throw new ValidationException("Erreur de validation : " + e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestException("Requête invalide : " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la création de l'entité : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<R> update(ID id, E entity) {
        try {
            E updatedEntity = service.update(id, entity);
            return ResponseEntity.ok(mapper.toResponseDto(updatedEntity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Entité non trouvée avec l'ID : " + id);
        } catch (ValidationException e) {
            throw new ValidationException("Erreur de validation : " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la mise à jour de l'entité : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<R> getById(ID id) {
        try {
            E foundEntity = service.getById(id);
            return ResponseEntity.ok(mapper.toResponseDto(foundEntity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Entité non trouvée avec l'ID : " + id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la récupération de l'entité : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<R>> getAll() {
        try {
            List<E> allEntities = service.getAll();
            return ResponseEntity.ok(allEntities.stream()
                    .map(mapper::toResponseDto)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la récupération des entités : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> delete(ID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Entité non trouvée avec l'ID : " + id);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Non autorisé à supprimer l'entité avec l'ID : " + id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la suppression de l'entité : " + e.getMessage());
        }
    }
}
