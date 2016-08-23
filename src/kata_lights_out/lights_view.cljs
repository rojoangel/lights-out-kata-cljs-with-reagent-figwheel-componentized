(ns kata-lights-out.lights-view
  (:require
    [reagent.core :as r]
    [kata-lights-out.lights :as l]))

(def ^:private light-on "1")
(def ^:private light-off "0")

(defn- all-lights-off-message-content [lc]
  (if (l/all-lights-off? lc)
    "Lights out, Yay!"
    {:style {:display :none}}))

(defn- all-lights-off-message-component [lc]
  [:div#all-off-msg
   (all-lights-off-message-content lc)])

(defn- on-light-click [pos lc]
  (l/flip-lights! lc pos))

(defn- render-light [light]
  (if (l/light-off? light)
    light-off
    light-on))

(defn- light-component [lc i j light]
  ^{:key (+ i j)}
  [:button
   {:on-click #(on-light-click [i j] lc)}
   (render-light light)])

(defn- row-lights-component [lc i row-lights]
  ^{:key i}
  [:div (map-indexed (partial light-component lc i) row-lights)])

(defn- home-page [{:keys [lights] :as lc}]
  [:div [:h2 "Kata Lights Out"]
   (map-indexed (partial row-lights-component lc) @lights)
   [all-lights-off-message-component lc]])

(defn mount [lights-component]
  (r/render
    [home-page lights-component]
    (.getElementById js/document "app")))