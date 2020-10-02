package unq.edu.tpi.desapp.model;

import javax.persistence.Entity;

@Entity
public class Suspended extends ProjectState {
    public Suspended() {
        super("Suspendido");
    }
}
