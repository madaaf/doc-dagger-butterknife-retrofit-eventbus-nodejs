exports.services = function(app){	
	app.get('/games/:codes/offers', function(req, res) {
		if(req.params.codes){
			console.log(req.params.codes);
			codes = req.params.codes.split(",");
			switch(true) {
				case codes.length<2:
					promo = 'promo1.json';
					break;
				case codes.length<4:
					promo = 'promo2.json';
					break;
				case codes.length>=4:
					promo = 'promo3.json';
					break;
			} 
		}
		res.sendFile(__dirname +'/data/'+promo);
	});
	
	app.get('/games', function(req, res) {
		res.sendFile(__dirname +'/data/games.json');
	});
}
