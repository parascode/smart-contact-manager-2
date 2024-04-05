/**
 *
 */
console.log("This is script file running");
const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    //if opened then
    // write code to close
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "0%");
  } else {
    // if closed
    // write code to open
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "20%");
  }
};

const search = () => {
  let query = $("#search-input").val();
  if (query == "") {
    $(".search-results").hide();
  } else {
    console.log(query);
    var url = `http://localhost:8282/search/${query}`;
    console.log(url);

    fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        console.log(data);
        let results = `<div class="list-group">`;
        data.forEach((contact) => {
          results += `<a href="/user/${contact.cId}/contact" class="list-group-item list-group-item-action">${contact.name}</a>`;
        });
        results += `</div>`;
        $(".search-results").html(results);
      });

    $(".search-results").show();
  }
};
