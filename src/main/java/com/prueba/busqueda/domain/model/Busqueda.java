package com.prueba.busqueda.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prueba.busqueda.shared.utils.GeneratedJacocoExcluded;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author fhidalgo
 */
public class Busqueda {
    
    @JsonIgnore
    private Long searchId;
    @Schema(description = "ID Hotel", name = "hotelId", example="1234aBc")
    private String hotelId;
    private String checkIn;
    private String checkOut;
    private List<Integer> ages;
    @JsonIgnore
    private String agesBd;

    public Busqueda() {
    }
    
    public Busqueda(
            final String hotelId, 
            final String checkIn, 
            final String checkOut, 
            final List<Integer> ages) {
        
        this.searchId = -1L;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }
    
    public Busqueda(
            final Long searchId,
            final String hotelId, 
            final String checkIn, 
            final String checkOut, 
            final List<Integer> ages) {
        
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }

    public Long getSearchId() {
        return searchId;
    }

    public void setSearchId(final Long searchId) {
        this.searchId = searchId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(final String hotelId) {
        this.hotelId = hotelId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(final String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(final String checkOut) {
        this.checkOut = checkOut;
    }

    public List<Integer> getAges() {
        return ages;
    }

    public void setAges(final List<Integer> ages) {
        this.ages = ages;
    }

    @GeneratedJacocoExcluded
    public String getAgesBd() {
        return agesBd;
    }

    @GeneratedJacocoExcluded
    public void setAgesBd(final String agesBd) {
        this.agesBd = agesBd;
    }
    
    public List<Integer> agesToList(final String agesBd) {
        return Arrays.asList(agesBd.split(","))
                .stream()
                .map(age -> Integer.valueOf(age))
                .toList();
    }

    @Override
    public String toString() {
        return "Busqueda{" + "searchId=" + searchId + ", hotelId=" + hotelId + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", ages=" + ages + '}';
    }

}
