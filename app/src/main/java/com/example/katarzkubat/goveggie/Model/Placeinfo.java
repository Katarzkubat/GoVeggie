package com.example.katarzkubat.goveggie.Model;


   public class Placeinfo {
        private double lat;
        private double lng;

        public Placeinfo(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String toString(){
            return "" + lat + ";" + lng;
        }
    }

