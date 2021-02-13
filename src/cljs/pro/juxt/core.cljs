(ns pro.juxt.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(defn app []
  [:h1 "Hello World!"])

(defn mount! []
  (rd/render [app] (.getElementById js/document "app")))

(defn init! []
  (mount!))

