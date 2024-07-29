package com.prueba.busqueda.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static com.prueba.busqueda.shared.constantes.Errores.*;
import com.prueba.busqueda.shared.utils.GeneratedJacocoExcluded;

/**
 *
 * @author fhidalgo
 */
public final class AltaBusquedaRequest {

    @Schema(description = "ID Hotel", name = "hotelId", example = "1234aBc")
    @NotEmpty(message = ERROR_HOTEL_ID_REQUIRED)
    private final String hotelId;

    @Schema(description = "Fecha de entrada", name = "checkIn", example = "08/07/2024")
    @NotEmpty(message = ERROR_FECHA_ENTRADA_VACIA)
    private final String checkIn;

    @Schema(description = "Fecha de salida", name = "checkOut", example = "11/07/2024")
    @NotEmpty(message = ERROR_FECHA_SALIDA_VACIA)
    private final String checkOut;

    @Schema(description = "ID Hotel", name = "ages", type = "object", example = "[30, 29, 1, 3]")
    @NotNull(message = ERROR_AGES_REQUIRED)
    private final List<Integer> ages;

    @JsonCreator
    public AltaBusquedaRequest(
            @JsonProperty("hotelId")
            final String hotelId,
            @JsonProperty("checkIn")
            final String checkIn,
            @JsonProperty("checkOut")
            final String checkOut,
            @JsonProperty("ages")
            final List<Integer> ages) {

        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public List<Integer> getAges() {
        return ages;
    }

    @GeneratedJacocoExcluded("no incluido en los test")
    public Busqueda toBusquedaObject() {
        return new Busqueda(hotelId, checkIn, checkOut, ages);
    }

    @Override
    public String toString() {
        return "AltaBusquedaRequest{" + "hotelId=" + hotelId + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", ages=" + ages + '}';
    }

    protected boolean canEqual(Object other) {
        return other instanceof AltaBusquedaRequest;
    }

    @Override
    @GeneratedJacocoExcluded("no incluido en los test")
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AltaBusquedaRequest)) {
            return false;
        }
        AltaBusquedaRequest other = (AltaBusquedaRequest) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        if (this.getHotelId() == null ? other.getHotelId() != null : !this.getHotelId().equals(other.getHotelId())) {
            return false;
        }
        if (this.getCheckIn() == null ? other.getCheckIn() != null : !this.getCheckIn().equals(other.getCheckIn())) {
            return false;
        }
        if (this.getCheckOut() == null ? other.getCheckOut() != null : !this.getCheckOut().equals(other.getCheckOut())) {
            return false;
        }
        return this.getAges().equals(other.getAges());
    }

    @Override
    @GeneratedJacocoExcluded("no incluido en los test")
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (hotelId == null ? 0 : hotelId.hashCode());
        hash = 31 * hash + (checkIn == null ? 0 : checkIn.hashCode());
        hash = 31 * hash + (checkOut == null ? 0 : checkOut.hashCode());
        hash = 31 * hash + (ages == null ? 0 : ages.hashCode());
        return hash;
    }

}
