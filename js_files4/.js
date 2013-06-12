function () {
      var search = document.getElementById('top-bar-search-input');
      var search_text = 'Search';
      if (search) {
        search.onfocus = function() {
          if (search.value == search_text) {
            search.value = '';
          }
        };
        search.onblur = function() {
          if (search.value == '') {
            search.value = search_text;
          }
        };
        document.getElementById('top-bar-search-button').onclick = function() {
          if (search.value != search_text && search.value != '') {
            search.form.submit();
          }
        };
        search.form.onsubmit = function() {
          if (search.value == search_text || search.value == '') {
            return false;
          }
        };
      }

      var subscribe = document.getElementById('subscribe-email');
      var subscribe_text = 'Your e-mail address';
      if (subscribe) {
        subscribe.onfocus = function() {
          if (subscribe.value == subscribe_text) {
            subscribe.value = ''
          }
        };
        subscribe.onblur = function() {
          if (subscribe.value == '') {
            subscribe.value = subscribe_text;
          }
        }
        subscribe.form.onsubmit = function() {
          if (subscribe.value == subscribe_text || subscribe.value == '') {
            return false;
          }
        };
      }
    }