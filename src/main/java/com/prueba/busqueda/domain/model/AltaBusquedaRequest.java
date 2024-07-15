package com.prueba.busqueda.domain.model;

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
public class AltaBusquedaRequest {

    @Schema(description = "ID Hotel", name = "hotelId", example = "1234aBc")
    @NotEmpty(message = ERROR_HOTEL_ID_REQUIRED)
    private String hotelId;

    @Schema(description = "Fecha de entrada", name = "checkIn", example = "08/07/2024")
    @NotEmpty(message = ERROR_FECHA_ENTRADA_VACIA)
    private String checkIn;

    @Schema(description = "Fecha de salida", name = "checkOut", example = "11/07/2024")
    @NotEmpty(message = ERROR_FECHA_SALIDA_VACIA)
    private String checkOut;

    @Schema(description = "ID Hotel", name = "ages", type = "object", example = "[30, 29, 1, 3]")
    @NotNull(message = ERROR_AGES_REQUIRED)
    private List<Integer> ages;

    public AltaBusquedaRequest() {

    }

    public AltaBusquedaRequest(
            final String hotelId,
            final String checkIn,
            final String checkOut,
            final List<Integer> ages) {

        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public List<Integer> getAges() {
        return ages;
    }

    public void setAges(List<Integer> ages) {
        this.ages = ages;
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

    public static class Builder {

        private String hotelId;
        private String checkIn;
        private String checkOut;
        private List<Integer> ages;

        public Builder() {
        }

        public Builder hotelId(String hotelId) {
            this.hotelId = hotelId;
            return this;
        }

        public Builder checkIn(String checkIn) {
            this.checkIn = checkIn;
            return this;
        }

        public Builder checkOut(String checkOut) {
            this.checkOut = checkOut;
            return this;
        }

        public Builder ages(List<Integer> ages) {
            this.ages = ages;
            return this;
        }

        public AltaBusquedaRequest build() {
            return new AltaBusquedaRequest(hotelId, checkIn, checkOut, ages);
        }
    }
}
