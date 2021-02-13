(ns pro.juxt.core
  (:require [reagent.dom :as rd]
            [mount.core :as mount]))

(defn mount-components! []
  (mount/start))

(defn app []
  [:h1 "Hello World!"])

(defn mount! []
  (rd/render [app] (.getElementById js/document "app")))

(defn init! []
  (mount-components!)
  (mount!))

