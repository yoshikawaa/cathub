/* init:activate functions */
$(function(){
  $('.ui.dropdown').dropdown();
});
/* init:clean javascript states */
$(window).on("unload",function(){});
/* action:submit form */
$('button[type="submit"]').on('click', function() {
  $('.ui.form').addClass("loading");
});
$('a[data-page]').on('click', function() {
  $('input[name="page"]').val($(this).attr('data-page'))
  $('.ui.form').addClass("loading");
  $(this).closest('form').submit();
});
$('input[name="size"]').on('change', function() {
  $('.ui.form').addClass("loading");
  $(this).closest('form').submit();
});
/* action:close message */
$('.message .close').on('click', function() {
  $(this).closest('.message').transition('fade');
});
/* action:show side-bar */
$('.togglable').click(function() {
  $('.ui.labeled.icon.sidebar').sidebar('toggle');
})
/* action:show modal */
$('.modalable').on('click', function() {
  $('.ui.modal').modal('setting', 'transition', 'slide down').modal('show');
});
