/**
 *
 */
console.log("This is script file running");
const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    //if opened then
    // write code to close
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "2%");
  } else {
    // if closed
    // write code to open
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "20%");
  }
};
