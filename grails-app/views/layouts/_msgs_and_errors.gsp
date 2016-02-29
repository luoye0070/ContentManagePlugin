<g:if test="${errors || flash.errors}">
    <div class="tips tips-small tips-warning">
        <span class="x-icon x-icon-small x-icon-error"><i class="icon icon-white icon-warning"></i></span>

        <div class="tips-content">${errors}${flash.errors}</div>
    </div>
</g:if>
<g:if test="${msgs || flash.msgs}">
    <div class="tips tips-small tips-info">
        <span class="x-icon x-icon-small x-icon-info"><i class="icon icon-white icon-info"></i></span>

        <div class="tips-content">${msgs}${flash.msgs}</div>
    </div>
</g:if>
<g:if test="${warnings || flash.warnings}">
    <div class="tips tips-small tips-warning">
        <span class="x-icon x-icon-small x-icon-warning"><i class="icon icon-white icon-bell"></i></span>
        <div class="tips-content">${flash.warnings}${warnings}</div>
    </div>
</g:if>
<g:if test="${success || flash.success}">
    <div class="tips tips-small  tips-success">
      <span class="x-icon x-icon-small x-icon-success"><i class="icon icon-white icon-ok"></i></span>
       <div class="tips-content">${flash.success}${success}</div>
        </div>
</g:if>
<g:if test="${question || flash.question}">
    <div class="tips tips-small">
      <span class="x-icon x-icon-small x-icon-warning"><i class="icon icon-white icon-question"></i></span>
       <div class="tips-content">${flash.question}${question}</div>
      </div>
</g:if>