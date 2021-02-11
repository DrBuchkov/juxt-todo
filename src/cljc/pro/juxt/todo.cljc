(ns pro.juxt.todo
  (:require [malli.core :as m]))

(def Todo
  [:map
   [:todo/title string?]
   [:todo/body {:optional true} string?]
   [:todo/status [:enum :todo :in-progress :done]]])

(def todo? (m/validator Todo))
