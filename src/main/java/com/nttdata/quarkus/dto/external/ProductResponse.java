package com.nttdata.quarkus.dto.external;

public record ProductResponse(Long id,String title,double price,
                              String description,String category,String image,
                              RatingResponse rating) {
    public record RatingResponse(double rate,int count){

    }
}
