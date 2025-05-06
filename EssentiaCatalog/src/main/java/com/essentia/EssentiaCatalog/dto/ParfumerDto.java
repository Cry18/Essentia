package com.essentia.EssentiaCatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ParfumerDto {
        
        @NotBlank(message = "il nome deve contenere almeno un carattere diverso da spazio")
        @Size(max = 30, message = "il nome del profumiere può contenere al massimo 30 caratteri")  
        private String name;
        @NotBlank(message = "la descrizione del profumiere deve contenere almeno un carattere diverso da spazio")
        @Size(min = 10, message = "la descrizione deve contenere almeno 10 caratteri")
        private String description;
        @NotBlank(message = "la nazionalità del profumiere deve contenere almeno un carattere diverso da spazio")
        @Size(max = 30, message = "la nazionalità del profumiere può contenere al massimo 30 caratteri")
        private String nazionality;
        
        public ParfumerDto() {}
        
        public ParfumerDto(String name, String description, String nazionality) {
            this.name = name;
            this.description = description;
            this.nazionality = nazionality;
        }
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getNazionality() {
            return nazionality;
        }
        public void setNazionality(String nazionality) {
            this.nazionality = nazionality;
        }
}

