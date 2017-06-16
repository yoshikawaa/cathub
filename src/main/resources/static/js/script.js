/* init:activate functions */
$(function() {
  $('.ui.dropdown').dropdown();
});
/* init:clean javascript states */
$(window).on("unload", function() {
});
/* action:submit form */
$('button[type="submit"]').on('click', function() {
  $(this).closest('.ui.form').addClass("loading");
});
$('.loading-timeout').on('click', function() {
  setTimeout($.proxy(function() {
    $(this).closest('.ui.form').removeClass("loading");
  }, this), 3000);
});
$('a[data-page]').on('click', function() {
  $('input[name="page"]').val($(this).attr('data-page'))
  $(this).closest('.ui.form').addClass("loading");
  $(this).closest('form').submit();
});
$('input[name="size"]').on('change', function() {
  $(this).closest('.ui.form').addClass("loading");
  $(this).closest('form').submit();
});
/* action:search candidates */
$('.ui.search').search({
  apiSettings : {
    url : 'repositories?&in=name&value={query}',
  },
  fields : {
    results : 'items',
    title : 'full_name'
  },
  minCharacters : 3
});
// $('.ui.search').search({
// apiSettings : {
// url : '/repositories?org={org}&in=name&value={value}'
// },
// urlData : {
// org : $('input[name="org"]').val(),
// value : $('input[name="value"]').val()
// },
// fields : {
// title : 'name',
// url : 'htmlUrl'
// },
// minCharacters : 3
// });
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
