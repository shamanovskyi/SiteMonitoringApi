package maxShamanovskyi.controller;

import maxShamanovskyi.service.WindowsService;

public class StatusController implements ApiController {

    public boolean checkStatus(WindowsService service) {
        return service.isWorking();
    }
}
