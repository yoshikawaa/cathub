/* action:submit form */
$('button[type="submit"]').on('click', function() {
  $('.form').addClass("loading");
});
$('a[data-page]').on('click', function() {
  $('input[name="page"]').val($(this).attr('data-page'))
  $('.form').addClass("loading");
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
