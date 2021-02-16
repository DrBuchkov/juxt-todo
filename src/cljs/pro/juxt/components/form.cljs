(ns pro.juxt.components.form
  (:require [pro.juxt.utils :refer [humanize]]
            [reagent.core :as r]
            [cljs.core.match :refer [match]]
            [malli.core :as m]
            [re-frame.core :as rf]))

(defn on-change [entity id]
  (fn [e]
    (swap! entity assoc id (-> e .-target .-value))))

(defn form-field-layout [field]
  [:div.w-full.px-3.mb-6.md:mb-0 {:class "md:w-1/2"}
   field])

(defn input [label type id entity]
  (form-field-layout
    [:div
     [:label.block.uppercase.tracking-wide.text-gray-700.text-xs.font-bold.mb-2 {:for id} label]
     [:input.form-control.appearance-none.block.w-full.bg-gray-100.text-gray-700.border.border-gray-500.rounded.py-3.px-4.mb-3.leading-tight.focus:outline-none.focus:bg-white
      {:field     type
       :id        id
       :value     (id @entity)
       :on-change (on-change entity id)}]]))

(defn text-area [label id entity]

  (form-field-layout
    [:div
     [:label.block.uppercase.tracking-wide.text-gray-700.text-xs.font-bold.mb-2 {:for id} label]
     [:textarea.form-control.appearance-none.block.w-full.bg-gray-100.text-gray-700.border.border-gray-500.rounded.py-3.px-4.mb-3.leading-tight.focus:outline-none.focus:bg-white
      {:field     :textarea
       :id        id
       :value     (or (id @entity) "")
       :on-change (on-change entity id)}]]))

(defn dropdown [label id items entity]
  (when-not (id @entity)
    (swap! entity assoc id (first items)))
  (form-field-layout
    [:div
     [:label.block.uppercase.tracking-wide.text-gray-700.text-xs.font-bold.mb-2 {:for id} label]
     [:div.relative
      [:select.block.appearance-none.w-full.bg-gray-100.border.border-gray-200.text-gray-700.py-3.px-4.pr-8.rounded.leading-tight.focus:outline-none.focus:bg-white.focus:border-gray-500
       {:id        id
        :value     (id @entity)
        :on-change (fn [e]
                     (swap! entity assoc id (keyword (-> e .-target .-value))))}
       (for [item items]
         ^{:key item} [:option {:value item}
                       (humanize (name item))])]
      [:div.pointer-events-none.absolute.inset-y-0.right-0.flex.items-center.px-2.text-gray-700
       [:svg.fill-current.h-4.w-4 {:xmlns "http://www.w3.org/2000/svg" :viewBox "0 0 20 20"} [:path {:d "M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"}]]]]]))

(defn spec-child->field [key value {:keys [field label type]} entity]
  (let [label (or label (humanize (name key)))]
    (match value
           [:enum & ?items] (dropdown label key ?items entity)
           value (let [type (or type
                                (condp = value
                                  'string? :text
                                  'number? :numeric))]
                   (condp = field
                     :textarea (text-area label key entity)
                     nil (input label type key entity))))))

(defn spec->fields [entity spec]
  (let [children (rest (m/form spec))]
    [:<>
     (for [child children]
       (match child
              [(?k :guard keyword?) ?v] ^{:key ?k} [spec-child->field ?k ?v {} entity]
              [(?k :guard keyword?) ?opts ?v] ^{:key ?k} [spec-child->field ?k ?v (:pro.juxt/form-opts ?opts) entity]))]))



(defn form-template [entity spec]
  [:div.p-6
   [spec->fields entity spec]])

(defn form [spec on-submit text init-val]
  (let [entity (r/atom (or init-val {}))]
    [:form {:on-submit (on-submit entity)}
     [form-template entity spec]
     [:button.mr-5.bg-green-600.hover:bg-green-700.text-white.font-bold.py-2.px-6.rounded-lg
      (or text "Save")]]))