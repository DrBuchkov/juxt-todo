(ns pro.juxt.components.table)


(defn table []
  [:div.p-6
   [:table.min-w-full.table-auto
    [:thead.justify-between
     [:tr.bg-gray-800
      [:th.px-16.py-2
       [:span.text-gray-300]]
      [:th.px-16.py-2
       [:span.text-gray-300 "Name"]]
      [:th.px-16.py-2
       [:span.text-gray-300 "Invitation"]]
      [:th.px-16.py-2
       [:span.text-gray-300 "Date"]]
      [:th.px-16.py-2
       [:span.text-gray-300 "Time"]]
      [:th.px-16.py-2
       [:span.text-gray-300 "Status"]]]]
    [:tbody.bg-gray-200
     [:tr.bg-white.border-4.border-gray-200
      [:td.px-16.py-2.flex.flex-row.items-center
       [:img.h-8.w-8.rounded-full.object-cover {:src "https://randomuser.me/api/portraits/men/30.jpg" :alt ""}]]
      [:td
       [:span.text-center.ml-2.font-semibold "Dean Lynch"]]
      [:td.px-16.py-2
       [:button.bg-indigo-500.text-white.px-4.py-2.border.rounded-md.hover:bg-white.hover:border-indigo-500.hover:text-black "Open Link"]]
      [:td.px-16.py-2
       [:span "05/06/2020"]]
      [:td.px-16.py-2
       [:span "10:00"]]
      [:td.px-16.py-2
       [:span.text-green-500
        [:svg.w-5.h5 {:xmlns "http://www.w3.org/2000/svg" :viewBox "0 0 24 24" :stroke-width "1.5" :stroke "#2c3e50" :fill "none" :stroke-linecap "round" :stroke-linejoin "round"}
         [:path {:stroke "none" :d "M0 0h24v24H0z"}]
         [:path {:d "M5 12l5 5l10 -10"}]]]]]
     [:tr.bg-white.border-4.border-gray-200
      [:td.px-16.py-2.flex.flex-row.items-center
       [:img.h-8.w-8.rounded-full.object-cover {:src "https://randomuser.me/api/portraits/men/76.jpg" :alt ""}]]
      [:td
       [:span.text-center.ml-2.font-semibold "Ralph Barnes"]]
      [:td.px-16.py-2
       [:button.bg-indigo-500.text-white.px-4.py-2.border.rounded-md.hover:bg-white.hover:border-indigo-500.hover:text-black "Open Link"]]
      [:td.px-16.py-2
       [:span "05/06/2020"]]
      [:td.px-16.py-2
       [:span "12:15"]]
      [:td.px-16.py-2
       [:span.text-yellow-500
        [:svg.w-5.h-5 {:xmlns "http://www.w3.org/2000/svg" :viewBox "0 0 24 24" :stroke-width "1.5" :stroke "#2c3e50" :fill "none" :stroke-linecap "round" :stroke-linejoin "round"}
         [:path {:stroke "none" :d "M0 0h24v24H0z"}]
         [:circle {:cx "12" :cy "12" :r "9"}]
         [:polyline {:points "12 7 12 12 15 15"}]]]]]
     [:tr.bg-white.border-4.border-gray-200
      [:td.px-16.py-2.flex.flex-row.items-center
       [:img.h-8.w-8.rounded-full.object-cover {:src "https://randomuser.me/api/portraits/men/38.jpg" :alt ""}]]
      [:td
       [:span.text-center.ml-2.font-semibold "Brett Castillo"]]
      [:td.px-16.py-2
       [:button.bg-indigo-500.text-white.px-4.py-2.border.rounded-md.hover:bg-white.hover:border-indigo-500.hover:text-black "Open Link"]]
      [:td.px-16.py-2
       [:span "05/06/2020"]]
      [:td.px-16.py-2
       [:span "08:35"]]
      [:td.px-16.py-2
       [:span.text-yellow-500
        [:svg.w-5.h-5 {:xmlns "http://www.w3.org/2000/svg" :viewBox "0 0 24 24" :stroke-width "1.5" :stroke "#2c3e50" :fill "none" :stroke-linecap "round" :stroke-linejoin "round"}
         [:path {:stroke "none" :d "M0 0h24v24H0z"}]
         [:line {:x1 "18" :y1 "6" :x2 "6" :y2 "18"}]
         [:line {:x1 "6" :y1 "6" :x2 "18" :y2 "18"}]]]]]]]])
