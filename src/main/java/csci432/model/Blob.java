package csci432.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Blob class for keeping track of moving items
 **/
public class Blob {
    @JsonProperty("locations")
    /**
     * Locations is a map from image index (int) to the location of the blob in the image(Point)
     */
    private Map<Integer, Point> locations;

    @JsonCreator
    /**
     * Should never be used, it this is for deserialization of blob
     */
    public Blob(Map<Integer, Point> locations) {
        this.locations =locations;
    }

    /**
     * Creates a new Hashmap and the Blob object
     */
    public Blob() {
        locations = new HashMap<>();
    }

    /**
     * Returns if the blob is in a given picture
     *
     * @param picIndex the index to look at
     * @return True if the blob is in the given picture, false otherwise
     */
    public Boolean inPicture(Integer picIndex) {
        return locations.containsKey(picIndex);
    }

    /**
     * Adds the current index to the location
     *
     * @param picIndex index of the picture
     * @param location location of the blob
     */
    public void addLocation(Integer picIndex, Point location) {
        locations.put(picIndex, location);
    }

    /**
     * Returns the location of the blob in the given picture index
     *
     * @param picIndex index of the picture
     * @returns the location of the blob, null if it isn't in the given picture
     */
    public Point getLocation(Integer picIndex) {
        return locations.get(picIndex);
    }

    /**
     * Returns the locations of the blobs in each picture
     *
     * @param start first index
     * @param end last index
     * @return a list of locations
     */
    public List<Point> getLocationsInRange(Integer start, Integer end) {
        List<Point> pointList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            pointList.add(locations.get(i));
        }

        return pointList;
    }

    @Override
    public String toString() {
        return "Blob:"+ locations.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Blob && this.locations.equals(((Blob) o).locations);
    }

    /**
     * Returns the start index and end index of the pictures
     *
     * @return returns the start index and end index of the pictures
     */
    public List<Integer> getPictureRange() {
        if (isEmpty()) {
            return null;
        }

        List<Integer> points = new ArrayList<>(locations.keySet());
        Collections.sort(points);
        return Arrays.asList(points.get(0), points.get(points.size()-1));
    }

    /**
     * Returns if there are some locations
     *
     * @return True if some locations exist, false otherwise
     */
    public Boolean isEmpty() {
        return locations.isEmpty();
    }

    /**
     * Returns all the locations in order
     *
     * @return List of points, the locations of the blob
     */
    public List<Point> getAllLocations() {
        if (isEmpty()) {
            return null;
        }

        List<Integer> indexes = getPictureRange();
        return getLocationsInRange(indexes.get(0), indexes.get(1));
    }

    /**
     * Saves the blob as a JSON file
     *
     * @param fileName file to save object as
     * @exception IOException ex happens when file can't be saved correctly
     */
    public void save(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(fileName), this);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads the blob from the JSON file
     *
     * @param fileName file to load object from
     * @exception IOException ex happens when the file can't be loaded correctly
     * @return The loaded blob
     */
    public static Blob load(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        Blob blob = null;
        try {
            blob = mapper.readValue(new File(fileName), Blob.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return blob;
        }
    }
}