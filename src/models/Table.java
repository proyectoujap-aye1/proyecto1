package models;

public class Table {
    private int number;
    private int personsInTable;
    private boolean isBusy;
    private Diner[] diners;

    // NUMBER
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if (number < 0)
            throw new IllegalArgumentException("El numero de mesa no puede ser negativo.");

        this.number = number;
    }

    // PERSONS IN TABLE
    public int getPersonsInTable() {
        return personsInTable;
    }

    public void setPersonsInTable(int personsInTable) {
        if (personsInTable < 0)
            throw new IllegalArgumentException("La cantidad de personas no puede ser negativa.");

        this.personsInTable = personsInTable;
    }

    // IS BUSY
    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    // DINERS
    public Diner[] getDiners() {
        return diners;
    }

    public void setDiners(Diner[] diners) {
        if (diners == null)
            throw new IllegalArgumentException("El arreglo de comensales no puede ser nulo.");

        this.diners = diners;
    }
}