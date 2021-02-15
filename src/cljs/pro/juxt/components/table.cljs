(ns pro.juxt.components.table
  (:require [pro.juxt.utils :as utils]
            [re-frame.core :as rf]))

(defn should-display? [child]
  (let [maybe-opts (second child)]
    (or (not (map? maybe-opts))
        (-> maybe-opts :pro.juxt/table-opts :hidden? not))))

(defn col-opts [child]
  (let [maybe-opts (second child)]
    (when (map? maybe-opts)
      (:pro.juxt/table-opts maybe-opts))))

(defn spec->cols [spec]
  (->> spec
       rest
       (filter should-display?)
       (map (comp (juxt identity (comp utils/humanize name)) first))))

(defn spec->opts [spec]
  (->> spec
       rest
       (filter should-display?)
       (map col-opts)))

(defn row [cols item opts actions]
  [:tr.bg-white.border-4.border-gray-200
   (map (fn [[key] {:keys [display-fn]}]
          ^{:key key}
          [:td.px-16.py-2
           (if display-fn
             [display-fn (key item) item]
             (key item))])
        cols opts)
   [:td.px-16.py-2
    (for [[key action] actions]
      ^{:key key} [action item])]])

(defn rows [cols items opts actions]
  [:<>
   (for [item items]
     ^{:key (:crux.db/id item)}
     [row cols item opts actions])])

(defn table [spec key actions]
  (fn [spec key]
    (let [items @(rf/subscribe [key])
          cols (if (not (empty? actions))
                 (concat (spec->cols spec)
                         [[:actions "Actions"]])
                 (spec->cols spec))
          opts (spec->opts spec)]
      [:div
       [:table.min-w-full.table-auto
        [:thead.justify-between
         [:tr.bg-gray-800
          (for [[key col] cols]
            ^{:key key}
            [:th.px-16.py-2
             [:span.text-gray-300 col]])]]
        [:tbody.bg-gray-200
         [rows cols items opts actions]]]])))
