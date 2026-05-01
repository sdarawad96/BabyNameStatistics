package edu.westga.comp2320.babynamestatistics.model;

/**
 * Represents one baby name record with a name, gender, year, and frequency.
 */
public class BabyNameRecord {

    private String name;
    private String gender;
    private int year;
    private int frequency;

    /**
     * Creates a baby name record.
     *
     * @param name the baby name
     * @param gender the gender, either M or F
     * @param year the year
     * @param frequency the frequency for the name
     */
    public BabyNameRecord(String name, String gender, int year, int frequency) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (gender == null || (!gender.equalsIgnoreCase("M") && !gender.equalsIgnoreCase("F"))) {
            throw new IllegalArgumentException("Gender must be M or F.");
        }
        if (year < 0) {
            throw new IllegalArgumentException("Year cannot be negative.");
        }
        if (frequency < 0) {
            throw new IllegalArgumentException("Frequency cannot be negative.");
        }

        this.name = name.trim();
        this.gender = gender.toUpperCase();
        this.year = year;
        this.frequency = frequency;
    }

    /**
     * Gets the baby name.
     *
     * @return the baby name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the gender.
     *
     * @return the gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Gets the year.
     *
     * @return the year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Gets the frequency.
     *
     * @return the frequency
     */
    public int getFrequency() {
        return this.frequency;
    }

    /**
     * Checks whether this record has the same identity as another record.
     *
     * @param other the other record
     * @return true if the name, gender, and year match
     */
    public boolean hasSameIdentity(BabyNameRecord other) {
        if (other == null) {
            return false;
        }

        return this.name.equalsIgnoreCase(other.getName())
                && this.gender.equals(other.getGender())
                && this.year == other.getYear();
    }

    /**
     * Returns this record as formatted text.
     *
     * @return the formatted record
     */
    @Override
    public String toString() {
        return this.name + " (" + this.gender + ", " + this.year + "): " + this.frequency;
    }
}