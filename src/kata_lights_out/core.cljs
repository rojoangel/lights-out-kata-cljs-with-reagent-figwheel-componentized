(ns kata-lights-out.core
  (:require
    [reagent.core :as r]
    [com.stuartsierra.component :as component]
    [kata-lights-out.lights :as l]
    [kata-lights-out.lights-view :as light-view]))

(enable-console-print!)

;; -------------------------
;; Initialize app
(defrecord MainComponent [lights-component n m]
  component/Lifecycle

  (start [this]
    (println ";; Starting main component")
	(l/reset-lights! lights-component n m)
  	(light-view/mount lights-component)
  	this)

  (stop [this]
    (println ";; Stopping lights component")
    this))

(defn main-component [n m]
  (map->MainComponent {:n n :m m}))	

(component/start
	(component/system-map
	  :lights-component (l/->LightsComponent)
	  :main (component/using
	         (main-component 4 4)
	         [:lights-component])))