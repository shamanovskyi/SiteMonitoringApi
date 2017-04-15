package maxShamanovskyi.controller;

import maxShamanovskyi.service.WindowsService;

public interface ApiController {

    boolean checkStatus(WindowsService service);
}
