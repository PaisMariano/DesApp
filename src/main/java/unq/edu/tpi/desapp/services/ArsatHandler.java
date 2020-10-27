package unq.edu.tpi.desapp.services;

import org.springframework.stereotype.Service;
import unq.edu.tpi.desapp.model.ArsatLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ArsatHandler {

    public List<ArsatLocation> getLocations() {
        return requestLocations();
    }

    public ArrayList<ArsatLocation> requestLocations() {
        //Aca deberia hacer la conexion y traer lo que necesite de la api que nos provean. Por ahora se hardcodea una lista de ArsatLocation
        ArsatLocation chascomus = new ArsatLocation("Chascomus", "Buenos Aires", 36647);
        ArsatLocation bragado = new ArsatLocation("Bragado", "Buenos Aires", 33222);
        ArsatLocation sanPedro = new ArsatLocation("San Pedro", "Buenos Aires", 45633);
        ArsatLocation lobos = new ArsatLocation("Lobos", "Buenos Aires", 23545);
        ArsatLocation junin = new ArsatLocation("Junin", "Buenos Aires", 58512);
        ArsatLocation generalBelgrano = new ArsatLocation("General Belgrano", "Buenos Aires", 12584);
        ArsatLocation villaGesell = new ArsatLocation("Villa Gesell", "Buenos Aires", 65822);
        ArsatLocation jesusMaria = new ArsatLocation("Jesus Maria", "Cordoba", 32541);
        ArsatLocation laCumbrecita = new ArsatLocation("La Cumbrecita", "Cordoba", 5682);
        ArsatLocation losCocos = new ArsatLocation("Los Cocos", "Cordoba", 6875);
        ArsatLocation staRosaCalamuchita = new ArsatLocation("Santa Rosa De Calamuchita", "Cordoba", 15268);
        ArsatLocation nihuil = new ArsatLocation("El Nihuil", "Mendoza", 9587);
        ArsatLocation maipu = new ArsatLocation("Maipu", "Mendoza", 35221);
        ArsatLocation uspallata = new ArsatLocation("Uspallata", "Mendoza", 15410);
        ArsatLocation cachi = new ArsatLocation("Cachi", "Salta", 5484);
        ArsatLocation elTala = new ArsatLocation("El Tala", "Salta", 1240);
        ArsatLocation guemes = new ArsatLocation("General Güemes (Salta)", "Salta", 2515);
        ArsatLocation lajitas = new ArsatLocation("Las Lajitas", "Salta", 1251);

        ArrayList<ArsatLocation> locations = new ArrayList();

        List<ArsatLocation> locationsList = Arrays.asList(
                chascomus,
                bragado,
                sanPedro,
                chascomus,
                bragado,
                sanPedro,
                lobos,
                junin,
                generalBelgrano,
                villaGesell,
                jesusMaria,
                laCumbrecita,
                losCocos,
                staRosaCalamuchita,
                nihuil,
                maipu,
                uspallata,
                cachi,
                elTala,
                guemes,
                lajitas);

        locations.addAll(locationsList);

        return locations;
    }
}
