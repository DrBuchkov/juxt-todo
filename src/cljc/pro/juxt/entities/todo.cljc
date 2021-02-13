(ns pro.juxt.entities.todo
  (:require [malli.core :as m]))

(def Todo
  [:map
   [:todo/title string?]
   [:todo/description {:optional true} string?]
   [:todo/status [:enum :todo :in-progress :done]]])

(def todo? (m/validator Todo))
