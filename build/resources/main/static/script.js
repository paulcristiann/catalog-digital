var sumaNote;
var nrNote;
var nume;
var teza;
function calcualtor(){
if(teza!=0)
$("#formTeza").hide();
else
$("#formTeza").show();
	$("#myModal").modal();
	$("#myModal .medie").text("Pana acum media la "+ nume + " este "+medie(sumaNote,nrNote,teza));
}

function medie(sumaNote2,nrNote2,teza2){

	var medieNote= Math.floor((sumaNote2/nrNote2) * 100) / 100;

	if(teza!=-1)
		return Math.floor((medieNote*3+teza2)/4* 100) / 100;
	else
		return medieNote
}

function rotunjeste(sumaNote2,nrNote2,teza2){
	var m=medie(sumaNote2,nrNote2,teza2)
	if(m-Math.floor(m)>=.5)
		return Math.ceil(m)
	else 
		return Math.floor(m)
}
function notePentru(nota,noteDeluat) {
	var sumNoteDeLuat;
	if(teza==-1)
		sumNoteDeLuat= Math.ceil((nota-.5)*(nrNote+noteDeluat)-sumaNote);
	else 
		sumNoteDeLuat=Math.ceil(((nota-.5)*4-teza)*(noteDeluat+nrNote)/3 - sumaNote)
	console.log(sumNoteDeLuat)
	var nrNoteNecesare=noteDeluat;
	var note=new Array(noteDeluat);
	while(nrNoteNecesare){
		note[nrNoteNecesare-1]=Math.ceil(sumNoteDeLuat/nrNoteNecesare)
		sumNoteDeLuat=sumNoteDeLuat-note[nrNoteNecesare-1]
		nrNoteNecesare--
	}
	return note
}
function calculeaza(){
var t=teza;
if(teza==0)
teza=parseInt($("#teza").val());
    $("#note").empty();
	var minim;
	var noteDeluat  = parseInt($("#nrNote").val());

	minim=rotunjeste(sumaNote+noteDeluat,nrNote+noteDeluat,teza)
	maxim=rotunjeste(sumaNote+10*noteDeluat,nrNote+noteDeluat,teza)

	$("#note").append($('<p>').text("Ai minim media "+minim).attr("class","note")) 
	for(i=minim+1;i<maxim;i++){
		$("#note").append($('<p>').text("media " +i +" : " +notePentru(i,noteDeluat)).attr("class","note"))
	}
	$("#note").append($('<p>').text("Iei maxim " +maxim +" : " +notePentru(maxim,noteDeluat)).attr("class","note"))
	teza=t;
}
window.onload=function(){
	$("#calculeaza").click(calculeaza);

	 var trs=document.getElementsByTagName("tr");
	 for(i=0;i<trs.length;i++){
	 	trs[i].addEventListener("click", function(){
            $("#note").empty();
	 		sumaNote=parseInt(this.getAttribute("suma-note"))
	 		nrNote=parseInt(this.getAttribute("numar-note"))
	 		nume=this.getAttribute("nume")
	 		teza=parseInt(this.getAttribute("teza"));

	 		calcualtor();

	 	});
	 }
}
