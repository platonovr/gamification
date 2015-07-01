/*!
 *
 *  Web Starter Kit
 *  Copyright 2015 Google Inc. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 *
 */

(function () {
  'use strict';



  // Your custom JavaScript goes here

  $('.event h3').on('click', function (e) {
    $(this).parent().children('.event_full_info').toggle();
    $(this).parent().children('.event_min_info').children('.icon-options_icon').toggle();
  });
  $('.icon-options_icon').on('click', function (e) {
    $(this).parent().parent().children('.event_full_info').toggle();
    $(this).toggle();
  });
  var availableTags1 = [
    "ActionScript",
    "AppleScript",
    "Asp",
    "BASIC",
    "C",
    "C++",
    "Clojure",
    "COBOL",
    "ColdFusion",
    "Erlang",
    "Fortran",
    "Groovy",
    "Haskell",
    "Java",
    "JavaScript",
    "Lisp",
    "Perl",
    "PHP",
    "Python",
    "Ruby",
    "Scala",
    "Scheme"
  ];
  var availableTags2 = [
    "ActionScript",
    "AppleScript",
    "Asp",
    "BASIC",
    "C",
    "C++",
    "Clojure",
    "COBOL",
    "ColdFusion",
    "Erlang",
    "Fortran",
    "Groovy",
    "Haskell",
    "Java",
    "JavaScript",
    "Lisp",
    "Perl",
    "PHP",
    "Python",
    "Ruby",
    "Scala",
    "Scheme"
  ];
  //group
  $( "#group" ).autocomplete({
    source: availableTags1,
    select: function(event, ui) {
      $('.group_elements').append('<div class="group_element"><span>'+ui.item.label+'</span> <span class="close_elem icon-close"></span> </div>');
      $( "#group").val("");
      return false;
    }
  });
  $('.group_elements').on('click', '.close_elem', function (e) {
    $(this).parent().remove();
  });

    //worker
  $( "#worker" ).autocomplete({
    source: availableTags2,
    select: function(event, ui) {
      $('.worker_elements').append('<div class="worker_element"><span>'+ui.item.label+'</span> <span class="close_elem icon-close"></span> </div>');
      $( "#worker").val("");
      return false;
    }
  });
  $('.worker_elements').on('click', '.close_elem', function (e) {
    $(this).parent().remove();
  });

  function handleFileSelect(evt) {
    var files = evt.target.files; // FileList object

    // files is a FileList of File objects. List some properties.
    var output = [];
    for (var i = 0, f; f = files[i]; i++) {
      output.push('<div class="file_element"><span>', escape(f.name),
        '</span> <span class="close_elem icon-close"></span> </div>');
    }
    document.getElementById('list').innerHTML = output.join('');
    console.log(output);
  }

  document.getElementById('files').addEventListener('change', handleFileSelect, false);
})();
