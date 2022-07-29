package com.example.cropdoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.gms.maps.model.Marker;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Terrains {
    public List<Marker> terrain;
    public List<Marker> trees;
    public String name;

    public static List<Terrains> terrainsList = new List<Terrains>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Terrains> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(Terrains terrains) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends Terrains> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends Terrains> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Terrains get(int index) {
            return null;
        }

        @Override
        public Terrains set(int index, Terrains element) {
            return null;
        }

        @Override
        public void add(int index, Terrains element) {

        }

        @Override
        public Terrains remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<Terrains> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Terrains> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<Terrains> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    public Terrains(String name, List<Marker> terrain, List<Marker> trees){
        this.terrain=terrain;
        this.trees=trees;
        this.name=name;
    }
}
