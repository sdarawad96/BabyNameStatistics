package edu.westga.comp2320.babynamestatistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages a collection of baby name records.
 * Prevents duplicate records with the same name, gender, and year.
 */
public class BabyNameCollection {

    private List<BabyNameRecord> records;

    /**
     * Creates an empty baby name collection.
     */
    public BabyNameCollection() {
        this.records = new ArrayList<>();
    }

    /**
     * Adds a baby name record to the collection.
     *
     * @param record the record to add
     * @throws IllegalArgumentException if the record is null or already exists
     */
    public void addRecord(BabyNameRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("Record cannot be null.");
        }

        if (this.containsDuplicate(record)) {
            throw new IllegalArgumentException("A record with the same name, gender, and year already exists.");
        }

        this.records.add(record);
    }

    /**
     * Removes a baby name record from the collection.
     *
     * @param record the record to remove
     * @return true if the record was removed, false otherwise
     */
    public boolean removeRecord(BabyNameRecord record) {
        return this.records.remove(record);
    }

    /**
     * Removes all records from the collection.
     */
    public void clear() {
        this.records.clear();
    }

    /**
     * Gets all baby name records.
     *
     * @return an unmodifiable list of records
     */
    public List<BabyNameRecord> getRecords() {
        return Collections.unmodifiableList(this.records);
    }

    /**
     * Checks whether the collection has any records.
     *
     * @return true if the collection has records, false otherwise
     */
    public boolean hasRecords() {
        return !this.records.isEmpty();
    }

    /**
     * Gets the number of records in the collection.
     *
     * @return the number of records
     */
    public int size() {
        return this.records.size();
    }

    /**
     * Checks whether a duplicate record exists.
     *
     * @param record the record to check
     * @return true if a duplicate exists, false otherwise
     */
    private boolean containsDuplicate(BabyNameRecord record) {
        for (BabyNameRecord currentRecord : this.records) {
            if (currentRecord.hasSameIdentity(record)) {
                return true;
            }
        }

        return false;
    }
}