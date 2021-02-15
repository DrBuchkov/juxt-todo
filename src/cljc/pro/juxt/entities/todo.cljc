(ns pro.juxt.entities.todo
  (:require [malli.core :as m]))

(def Todo
  [:map
   [:todo/title {:pro.juxt/form-opts {:label "What to do?"}} string?]
   [:todo/description
    {:optional            true
     :pro.juxt/form-opts  {:field :textarea}
     :pro.juxt/table-opts {:hidden? true}}
    string?]
   [:todo/status
    {:pro.juxt/table-opts {:display-fn
                           (fn [value _]
                             (case value
                               :todo [:div.text-xs.inline-flex.items-center.font-bold.leading-sm.uppercase.px-3.py-1.bg-blue-200.text-blue-700.rounded-full value]
                               :in-progress [:div.text-xs.inline-flex.items-center.font-bold.leading-sm.uppercase.px-3.py-1.bg-yellow-200.text-yellow-700.rounded-full value]
                               :done [:div.text-xs.inline-flex.items-center.font-bold.leading-sm.uppercase.px-3.py-1.bg-green-200.text-green-700.rounded-full value]))}}
    [:enum :todo :in-progress :done]]])

(def todo? (m/validator Todo))
