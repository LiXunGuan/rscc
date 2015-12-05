AJS.$(document).ready(function(){

    /**
     * Flat Pack Tweaks
     * Version type detection is naive, just supports prod, milestone and snapshot.
     */
    var version = AJS.version,
        versionLozenge = AJS.$('#header-version'),
        versionListItem = AJS.$('<li>Version: ' + version + '</li>'),
        isSnapshot = (version.toLowerCase().indexOf("snapshot") >= 0),
        isMilestone = (version.toLowerCase().indexOf("m") >= 0);

    // Adjust version lozenge according to version type
    if ( isSnapshot ) {
        versionLozenge.addClass("aui-lozenge-error");
    } else if ( isMilestone ) {
        versionLozenge.addClass("aui-lozenge-current");
    } else {
        versionLozenge.addClass("aui-lozenge-success");
    }

    // Quick version awareness, to avoid having to double-process the soy.
    AJS.$("#aui-footer-list").append(versionListItem);

    // Consolepeek promo
    AJS.log("Like great design and digging into the code? We're hiring! http://bit.ly/Y9xoQu");

    // Dialog handlers
    AJS.$("#cdn-button").click(function() {
        AJS.dialog2("#cdn-dialog").show();
    });
    AJS.$("#cdn-dialog-close").click(function() {
        AJS.dialog2("#cdn-dialog").hide();
    });

    AJS.$("#download-flatpack-link").click(function() {
        AJS.dialog2("#download-dialog").show();
    });
    AJS.$("#download-dialog-close").click(function() {
        AJS.dialog2("#download-dialog").hide();
    });
    
});