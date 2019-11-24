function addDrug(drug)
{
	console.log(drug);

	let tr=$('<tr></tr>');
	let tdCode=$('<td>'+ drug.code +'</td>');
	let tdName=$('<td>'+ drug.name +'</td>');
	let tdEdit=$('<td style="text-align:right;"><button class="btn btn-link "><i class="fas fa-edit"></i></button></td>');
	let tdDelete=$('<td style="text-align:right;width:10px"><button class="btn btn-link "><i class="fas fa-trash"></i></button></td>');

 //   tdEdit.click(editDrug(drug));
 //    tdDelete.click(deleteDrug(drug));

	tr.append(tdCode).append(tdName).append(tdEdit).append(tdDelete);
	$('#tableDrugs tbody').append(tr);

}