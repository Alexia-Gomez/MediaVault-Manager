package models;

public class Operation {
	
	public int id_operation;
	public String operation_type, operation_date;
	public double precioUnitario, precioFinal;
	public Client cliente;
	public Movie movie;
	public Game game;

	//Constructor para recibir movie
	public Operation(int id_operation, Client cliente, Movie movie, String operation_type, String operation_date, double precioUnitario, double precioFinal) {
		this.id_operation=id_operation;
		this.cliente=cliente;
		this.movie=movie;
		this.operation_type=operation_type;
		this.operation_date=operation_date;
		this.precioUnitario=precioUnitario;
		this.precioFinal=precioFinal;
	}
	
	//Constructor para recibir game
	public Operation(int id_operation, Client cliente, Game game, String operation_type, String operation_date, double precioUnitario, double precioFinal) {
		this.id_operation=id_operation;
		this.cliente=cliente;
		this.game=game;
		this.operation_type=operation_type;
		this.operation_date=operation_date;
		this.precioUnitario=precioUnitario;
		this.precioFinal=precioFinal;
	}
	
	public Operation( Client cliente, Movie movie, String operation_type, String operation_date, double precioUnitario, double precioFinal) {
		this.cliente=cliente;
		this.movie=movie;
		this.operation_type=operation_type;
		this.operation_date=operation_date;
		this.precioUnitario=precioUnitario;
		this.precioFinal=precioFinal;
	}
	
	public Operation( Client cliente, Game game, String operation_type, String operation_date, double precioUnitario, double precioFinal) {
		this.cliente=cliente;
		this.game=game;
		this.operation_type=operation_type;
		this.operation_date=operation_date;
		this.precioUnitario=precioUnitario;
		this.precioFinal=precioFinal;
	}

	public String getProductTitle() {
	        if (movie != null) {
	            return movie.title;
	        } else if (game != null) {
	            return game.title;
	        } else {
	            return "";
	        }
	}
	
	public int getId_operation() {
		return id_operation;
	}

	public void setId_operation(int id_operation) {
		this.id_operation = id_operation;
	}

	public String getOperation_type() {
		return operation_type;
	}

	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}

	public String getOperation_date() {
		return operation_date;
	}

	public void setOperation_date(String operation_date) {
		this.operation_date = operation_date;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}

	public Client getCliente() {
		return cliente;
	}

	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	
}
