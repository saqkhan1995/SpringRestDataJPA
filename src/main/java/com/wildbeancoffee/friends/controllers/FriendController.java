package com.wildbeancoffee.friends.controllers;

import com.wildbeancoffee.friends.model.Friend;
import com.wildbeancoffee.friends.services.FriendService;
import com.wildbeancoffee.friends.util.ErrorMessage;
import com.wildbeancoffee.friends.util.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FriendController {

  @Autowired
  FriendService friendService;

  @PostMapping("/friend")
  Friend create(@Valid @RequestBody Friend friend) throws ValidationException {
    /*if (friend.getId() == 0 && friend.getFirstName()!=null && friend.getLastName()!=null) {
      return friendService.save(friend);
    } else {
      throw new ValidationException("friend cannot be created!");
    }*/
    // Using JSR-380 Validations to achieve the same as above
    // Use @Valid & throw Exception in method signature & in Friend class use Annotations like @NotNull
    return friendService.save(friend);

  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e ) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());

    return fieldErrorMessages;
  }

  @GetMapping("/friend")
  Iterable<Friend> read() {
    return friendService.findAll();
  }

  @PutMapping("/friend")
  Friend update(@RequestBody Friend friend) {
    return friendService.save(friend);
  }

  @DeleteMapping("/friend/{id}")
  void delete(@PathVariable Integer id) {
    friendService.deleteById(id);
  }

  @GetMapping("/friend/{id}")
  Optional<Friend> findById(@PathVariable Integer id) {
    return friendService.findById(id);
  }

  @GetMapping("/friend/search")
  Iterable<Friend> findByQuery(
          /*@RequestParam("first") String firstName,*/                         //here "first" is always required by default
          @RequestParam(value = "first", required = false) String firstName,   //here "first" may or may not be provided, hence marked as required=false
          @RequestParam(value="last", required = false) String lastName) {     //here "last" may or may not be provided, hence marked as required=false
    if (firstName!=null && lastName!=null) {
      return friendService.findByFirstNameAndLastName(firstName, lastName);
    } else if (firstName!=null) {
      return friendService.findByFirstName(firstName);
    } else if (lastName!=null) {
      return friendService.findByLastName(lastName);
    } else {
      return friendService.findAll();
    }

  }

  @GetMapping("/wrong")
  Friend somethingIsWrong() {
    throw new ValidationException("Something is wrong");
  }

}
